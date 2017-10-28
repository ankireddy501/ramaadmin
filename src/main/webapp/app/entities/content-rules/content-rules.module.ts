import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RamaadminSharedModule } from '../../shared';
import {
    ContentRulesService,
    ContentRulesPopupService,
    ContentRulesComponent,
    ContentRulesDetailComponent,
    ContentRulesDialogComponent,
    ContentRulesPopupComponent,
    ContentRulesDeletePopupComponent,
    ContentRulesDeleteDialogComponent,
    contentRulesRoute,
    contentRulesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contentRulesRoute,
    ...contentRulesPopupRoute,
];

@NgModule({
    imports: [
        RamaadminSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContentRulesComponent,
        ContentRulesDetailComponent,
        ContentRulesDialogComponent,
        ContentRulesDeleteDialogComponent,
        ContentRulesPopupComponent,
        ContentRulesDeletePopupComponent,
    ],
    entryComponents: [
        ContentRulesComponent,
        ContentRulesDialogComponent,
        ContentRulesPopupComponent,
        ContentRulesDeleteDialogComponent,
        ContentRulesDeletePopupComponent,
    ],
    providers: [
        ContentRulesService,
        ContentRulesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RamaadminContentRulesModule {}
