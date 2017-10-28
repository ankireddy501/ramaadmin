import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RamaadminSharedModule } from '../../shared';
import {
    MovieContentDetailsService,
    MovieContentDetailsPopupService,
    MovieContentDetailsComponent,
    MovieContentDetailsDetailComponent,
    MovieContentDetailsDialogComponent,
    MovieContentDetailsPopupComponent,
    MovieContentDetailsDeletePopupComponent,
    MovieContentDetailsDeleteDialogComponent,
    movieContentDetailsRoute,
    movieContentDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...movieContentDetailsRoute,
    ...movieContentDetailsPopupRoute,
];

@NgModule({
    imports: [
        RamaadminSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MovieContentDetailsComponent,
        MovieContentDetailsDetailComponent,
        MovieContentDetailsDialogComponent,
        MovieContentDetailsDeleteDialogComponent,
        MovieContentDetailsPopupComponent,
        MovieContentDetailsDeletePopupComponent,
    ],
    entryComponents: [
        MovieContentDetailsComponent,
        MovieContentDetailsDialogComponent,
        MovieContentDetailsPopupComponent,
        MovieContentDetailsDeleteDialogComponent,
        MovieContentDetailsDeletePopupComponent,
    ],
    providers: [
        MovieContentDetailsService,
        MovieContentDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RamaadminMovieContentDetailsModule {}
