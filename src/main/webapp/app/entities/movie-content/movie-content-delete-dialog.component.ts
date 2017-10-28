import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MovieContent } from './movie-content.model';
import { MovieContentPopupService } from './movie-content-popup.service';
import { MovieContentService } from './movie-content.service';

@Component({
    selector: 'jhi-movie-content-delete-dialog',
    templateUrl: './movie-content-delete-dialog.component.html'
})
export class MovieContentDeleteDialogComponent {

    movieContent: MovieContent;

    constructor(
        private movieContentService: MovieContentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieContentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'movieContentListModification',
                content: 'Deleted an movieContent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-content-delete-popup',
    template: ''
})
export class MovieContentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieContentPopupService: MovieContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.movieContentPopupService
                .open(MovieContentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
