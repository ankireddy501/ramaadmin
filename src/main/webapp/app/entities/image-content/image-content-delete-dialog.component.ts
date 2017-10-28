import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImageContent } from './image-content.model';
import { ImageContentPopupService } from './image-content-popup.service';
import { ImageContentService } from './image-content.service';

@Component({
    selector: 'jhi-image-content-delete-dialog',
    templateUrl: './image-content-delete-dialog.component.html'
})
export class ImageContentDeleteDialogComponent {

    imageContent: ImageContent;

    constructor(
        private imageContentService: ImageContentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imageContentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imageContentListModification',
                content: 'Deleted an imageContent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-image-content-delete-popup',
    template: ''
})
export class ImageContentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imageContentPopupService: ImageContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imageContentPopupService
                .open(ImageContentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
