import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ImageContent } from './image-content.model';
import { ImageContentService } from './image-content.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-image-content',
    templateUrl: './image-content.component.html'
})
export class ImageContentComponent implements OnInit, OnDestroy {
imageContents: ImageContent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private imageContentService: ImageContentService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.imageContentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.imageContents = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInImageContents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ImageContent) {
        return item.id;
    }
    registerChangeInImageContents() {
        this.eventSubscriber = this.eventManager.subscribe('imageContentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
