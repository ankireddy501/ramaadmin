import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { MovieContent } from './movie-content.model';
import { MovieContentService } from './movie-content.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-movie-content',
    templateUrl: './movie-content.component.html'
})
export class MovieContentComponent implements OnInit, OnDestroy {
movieContents: MovieContent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private movieContentService: MovieContentService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.movieContentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.movieContents = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMovieContents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MovieContent) {
        return item.id;
    }
    registerChangeInMovieContents() {
        this.eventSubscriber = this.eventManager.subscribe('movieContentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
