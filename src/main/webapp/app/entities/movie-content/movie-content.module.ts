import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RamaadminSharedModule } from '../../shared';
import {
    MovieContentService,
    MovieContentPopupService,
    MovieContentComponent,
    MovieContentDetailComponent,
    MovieContentDialogComponent,
    MovieContentPopupComponent,
    MovieContentDeletePopupComponent,
    MovieContentDeleteDialogComponent,
    movieContentRoute,
    movieContentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...movieContentRoute,
    ...movieContentPopupRoute,
];

@NgModule({
    imports: [
        RamaadminSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MovieContentComponent,
        MovieContentDetailComponent,
        MovieContentDialogComponent,
        MovieContentDeleteDialogComponent,
        MovieContentPopupComponent,
        MovieContentDeletePopupComponent,
    ],
    entryComponents: [
        MovieContentComponent,
        MovieContentDialogComponent,
        MovieContentPopupComponent,
        MovieContentDeleteDialogComponent,
        MovieContentDeletePopupComponent,
    ],
    providers: [
        MovieContentService,
        MovieContentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RamaadminMovieContentModule {}
