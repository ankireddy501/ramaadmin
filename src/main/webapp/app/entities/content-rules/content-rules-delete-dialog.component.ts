import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContentRules } from './content-rules.model';
import { ContentRulesPopupService } from './content-rules-popup.service';
import { ContentRulesService } from './content-rules.service';

@Component({
    selector: 'jhi-content-rules-delete-dialog',
    templateUrl: './content-rules-delete-dialog.component.html'
})
export class ContentRulesDeleteDialogComponent {

    contentRules: ContentRules;

    constructor(
        private contentRulesService: ContentRulesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentRulesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contentRulesListModification',
                content: 'Deleted an contentRules'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-rules-delete-popup',
    template: ''
})
export class ContentRulesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentRulesPopupService: ContentRulesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contentRulesPopupService
                .open(ContentRulesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
