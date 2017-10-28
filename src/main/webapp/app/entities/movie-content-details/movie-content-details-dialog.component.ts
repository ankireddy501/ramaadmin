import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MovieContentDetails } from './movie-content-details.model';
import { MovieContentDetailsPopupService } from './movie-content-details-popup.service';
import { MovieContentDetailsService } from './movie-content-details.service';

@Component({
    selector: 'jhi-movie-content-details-dialog',
    templateUrl: './movie-content-details-dialog.component.html'
})
export class MovieContentDetailsDialogComponent implements OnInit {

    movieContentDetails: MovieContentDetails;
    isSaving: boolean;
    releaseDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private movieContentDetailsService: MovieContentDetailsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.movieContentDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.movieContentDetailsService.update(this.movieContentDetails));
        } else {
            this.subscribeToSaveResponse(
                this.movieContentDetailsService.create(this.movieContentDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<MovieContentDetails>) {
        result.subscribe((res: MovieContentDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MovieContentDetails) {
        this.eventManager.broadcast({ name: 'movieContentDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-movie-content-details-popup',
    template: ''
})
export class MovieContentDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieContentDetailsPopupService: MovieContentDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.movieContentDetailsPopupService
                    .open(MovieContentDetailsDialogComponent as Component, params['id']);
            } else {
                this.movieContentDetailsPopupService
                    .open(MovieContentDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
