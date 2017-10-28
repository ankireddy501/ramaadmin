import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RamaadminSharedModule } from '../../shared';
import {
    ContentService,
    ContentPopupService,
    ContentComponent,
    ContentDetailComponent,
    ContentDialogComponent,
    ContentPopupComponent,
    ContentDeletePopupComponent,
    ContentDeleteDialogComponent,
    contentRoute,
    contentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contentRoute,
    ...contentPopupRoute,
];

@NgModule({
    imports: [
        RamaadminSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContentComponent,
        ContentDetailComponent,
        ContentDialogComponent,
        ContentDeleteDialogComponent,
        ContentPopupComponent,
        ContentDeletePopupComponent,
    ],
    entryComponents: [
        ContentComponent,
        ContentDialogComponent,
        ContentPopupComponent,
        ContentDeleteDialogComponent,
        ContentDeletePopupComponent,
    ],
    providers: [
        ContentService,
        ContentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RamaadminContentModule {}
