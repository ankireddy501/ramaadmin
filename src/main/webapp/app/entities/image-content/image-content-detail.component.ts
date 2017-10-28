import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ImageContent } from './image-content.model';
import { ImageContentService } from './image-content.service';

@Component({
    selector: 'jhi-image-content-detail',
    templateUrl: './image-content-detail.component.html'
})
export class ImageContentDetailComponent implements OnInit, OnDestroy {

    imageContent: ImageContent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private imageContentService: ImageContentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImageContents();
    }

    load(id) {
        this.imageContentService.find(id).subscribe((imageContent) => {
            this.imageContent = imageContent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImageContents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imageContentListModification',
            (response) => this.load(this.imageContent.id)
        );
    }
}
