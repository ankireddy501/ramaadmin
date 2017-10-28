import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContentRules } from './content-rules.model';
import { ContentRulesPopupService } from './content-rules-popup.service';
import { ContentRulesService } from './content-rules.service';
import { Content, ContentService } from '../content';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-content-rules-dialog',
    templateUrl: './content-rules-dialog.component.html'
})
export class ContentRulesDialogComponent implements OnInit {

    contentRules: ContentRules;
    isSaving: boolean;

    contents: Content[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contentRulesService: ContentRulesService,
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
        if (this.contentRules.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contentRulesService.update(this.contentRules));
        } else {
            this.subscribeToSaveResponse(
                this.contentRulesService.create(this.contentRules));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContentRules>) {
        result.subscribe((res: ContentRules) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ContentRules) {
        this.eventManager.broadcast({ name: 'contentRulesListModification', content: 'OK'});
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
    selector: 'jhi-content-rules-popup',
    template: ''
})
export class ContentRulesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentRulesPopupService: ContentRulesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contentRulesPopupService
                    .open(ContentRulesDialogComponent as Component, params['id']);
            } else {
                this.contentRulesPopupService
                    .open(ContentRulesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
