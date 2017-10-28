import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MovieContent } from './movie-content.model';
import { MovieContentService } from './movie-content.service';

@Component({
    selector: 'jhi-movie-content-detail',
    templateUrl: './movie-content-detail.component.html'
})
export class MovieContentDetailComponent implements OnInit, OnDestroy {

    movieContent: MovieContent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private movieContentService: MovieContentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMovieContents();
    }

    load(id) {
        this.movieContentService.find(id).subscribe((movieContent) => {
            this.movieContent = movieContent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMovieContents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'movieContentListModification',
            (response) => this.load(this.movieContent.id)
        );
    }
}
