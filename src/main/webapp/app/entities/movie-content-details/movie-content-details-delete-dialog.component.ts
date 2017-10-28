import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MovieContentDetails } from './movie-content-details.model';
import { MovieContentDetailsPopupService } from './movie-content-details-popup.service';
import { MovieContentDetailsService } from './movie-content-details.service';

@Component({
    selector: 'jhi-movie-content-details-delete-dialog',
    templateUrl: './movie-content-details-delete-dialog.component.html'
})
export class MovieContentDetailsDeleteDialogComponent {

    movieContentDetails: MovieContentDetails;

    constructor(
        private movieContentDetailsService: MovieContentDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieContentDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'movieContentDetailsListModification',
                content: 'Deleted an movieContentDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-content-details-delete-popup',
    template: ''
})
export class MovieContentDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieContentDetailsPopupService: MovieContentDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.movieContentDetailsPopupService
                .open(MovieContentDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
