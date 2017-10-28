import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Content } from './content.model';
import { ContentPopupService } from './content-popup.service';
import { ContentService } from './content.service';
import { MovieContent, MovieContentService } from '../movie-content';
import { ContentRules, ContentRulesService } from '../content-rules';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-content-dialog',
    templateUrl: './content-dialog.component.html'
})
export class ContentDialogComponent implements OnInit {

    content: Content;
    isSaving: boolean;

    videos: MovieContent[];

    rules: ContentRules[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contentService: ContentService,
        private movieContentService: MovieContentService,
        private contentRulesService: ContentRulesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.movieContentService
            .query({filter: 'content-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.content.video || !this.content.video.id) {
                    this.videos = res.json;
                } else {
                    this.movieContentService
                        .find(this.content.video.id)
                        .subscribe((subRes: MovieContent) => {
                            this.videos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.contentRulesService
            .query({filter: 'content-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.content.rules || !this.content.rules.id) {
                    this.rules = res.json;
                } else {
                    this.contentRulesService
                        .find(this.content.rules.id)
                        .subscribe((subRes: ContentRules) => {
                            this.rules = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.content.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contentService.update(this.content));
        } else {
            this.subscribeToSaveResponse(
                this.contentService.create(this.content));
        }
    }

    private subscribeToSaveResponse(result: Observable<Content>) {
        result.subscribe((res: Content) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Content) {
        this.eventManager.broadcast({ name: 'contentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackMovieContentById(index: number, item: MovieContent) {
        return item.id;
    }

    trackContentRulesById(index: number, item: ContentRules) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-content-popup',
    template: ''
})
export class ContentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentPopupService: ContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contentPopupService
                    .open(ContentDialogComponent as Component, params['id']);
            } else {
                this.contentPopupService
                    .open(ContentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
