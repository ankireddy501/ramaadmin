import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ImageContent } from './image-content.model';
import { ImageContentPopupService } from './image-content-popup.service';
import { ImageContentService } from './image-content.service';
import { Content, ContentService } from '../content';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-image-content-dialog',
    templateUrl: './image-content-dialog.component.html'
})
export class ImageContentDialogComponent implements OnInit {

    imageContent: ImageContent;
    isSaving: boolean;

    contents: Content[];
    creationDateDp: any;
    updateDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private imageContentService: ImageContentService,
        private contentService: ContentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contentService.query()
            .subscribe((res: ResponseWrapper) => { this.contents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.imageContent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imageContentService.update(this.imageContent));
        } else {
            this.subscribeToSaveResponse(
                this.imageContentService.create(this.imageContent));
        }
    }

    private subscribeToSaveResponse(result: Observable<ImageContent>) {
        result.subscribe((res: ImageContent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ImageContent) {
        this.eventManager.broadcast({ name: 'imageContentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackContentById(index: number, item: Content) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-image-content-popup',
    template: ''
})
export class ImageContentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imageContentPopupService: ImageContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imageContentPopupService
                    .open(ImageContentDialogComponent as Component, params['id']);
            } else {
                this.imageContentPopupService
                    .open(ImageContentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
