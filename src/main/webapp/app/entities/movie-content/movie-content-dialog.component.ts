import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MovieContent } from './movie-content.model';
import { MovieContentPopupService } from './movie-content-popup.service';
import { MovieContentService } from './movie-content.service';
import { MovieContentDetails, MovieContentDetailsService } from '../movie-content-details';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-content-dialog',
    templateUrl: './movie-content-dialog.component.html'
})
export class MovieContentDialogComponent implements OnInit {

    movieContent: MovieContent;
    isSaving: boolean;

    details: MovieContentDetails[];
    creationTimeDp: any;
    updateDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private movieContentService: MovieContentService,
        private movieContentDetailsService: MovieContentDetailsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.movieContentDetailsService
            .query({filter: 'moviecontent-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.movieContent.details || !this.movieContent.details.id) {
                    this.details = res.json;
                } else {
                    this.movieContentDetailsService
                        .find(this.movieContent.details.id)
                        .subscribe((subRes: MovieContentDetails) => {
                            this.details = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.movieContent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.movieContentService.update(this.movieContent));
        } else {
            this.subscribeToSaveResponse(
                this.movieContentService.create(this.movieContent));
        }
    }

    private subscribeToSaveResponse(result: Observable<MovieContent>) {
        result.subscribe((res: MovieContent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MovieContent) {
        this.eventManager.broadcast({ name: 'movieContentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackMovieContentDetailsById(index: number, item: MovieContentDetails) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-movie-content-popup',
    template: ''
})
export class MovieContentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieContentPopupService: MovieContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.movieContentPopupService
                    .open(MovieContentDialogComponent as Component, params['id']);
            } else {
                this.movieContentPopupService
                    .open(MovieContentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
