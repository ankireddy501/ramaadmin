import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MovieContentDetails } from './movie-content-details.model';
import { MovieContentDetailsService } from './movie-content-details.service';

@Component({
    selector: 'jhi-movie-content-details-detail',
    templateUrl: './movie-content-details-detail.component.html'
})
export class MovieContentDetailsDetailComponent implements OnInit, OnDestroy {

    movieContentDetails: MovieContentDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private movieContentDetailsService: MovieContentDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMovieContentDetails();
    }

    load(id) {
        this.movieContentDetailsService.find(id).subscribe((movieContentDetails) => {
            this.movieContentDetails = movieContentDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMovieContentDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'movieContentDetailsListModification',
            (response) => this.load(this.movieContentDetails.id)
        );
    }
}
