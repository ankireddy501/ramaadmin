import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ContentRules } from './content-rules.model';
import { ContentRulesService } from './content-rules.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-content-rules',
    templateUrl: './content-rules.component.html'
})
export class ContentRulesComponent implements OnInit, OnDestroy {
contentRules: ContentRules[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contentRulesService: ContentRulesService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contentRulesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.contentRules = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContentRules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ContentRules) {
        return item.id;
    }
    registerChangeInContentRules() {
        this.eventSubscriber = this.eventManager.subscribe('contentRulesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
