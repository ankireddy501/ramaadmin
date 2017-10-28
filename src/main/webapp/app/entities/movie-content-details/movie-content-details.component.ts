import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { MovieContentDetails } from './movie-content-details.model';
import { MovieContentDetailsService } from './movie-content-details.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-movie-content-details',
    templateUrl: './movie-content-details.component.html'
})
export class MovieContentDetailsComponent implements OnInit, OnDestroy {
movieContentDetails: MovieContentDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private movieContentDetailsService: MovieContentDetailsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.movieContentDetailsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.movieContentDetails = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMovieContentDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MovieContentDetails) {
        return item.id;
    }
    registerChangeInMovieContentDetails() {
        this.eventSubscriber = this.eventManager.subscribe('movieContentDetailsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
