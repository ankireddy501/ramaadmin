import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ContentRules } from './content-rules.model';
import { ContentRulesService } from './content-rules.service';

@Component({
    selector: 'jhi-content-rules-detail',
    templateUrl: './content-rules-detail.component.html'
})
export class ContentRulesDetailComponent implements OnInit, OnDestroy {

    contentRules: ContentRules;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contentRulesService: ContentRulesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContentRules();
    }

    load(id) {
        this.contentRulesService.find(id).subscribe((contentRules) => {
            this.contentRules = contentRules;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContentRules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contentRulesListModification',
            (response) => this.load(this.contentRules.id)
        );
    }
}
