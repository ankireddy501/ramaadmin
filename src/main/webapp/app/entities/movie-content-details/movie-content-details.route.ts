import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MovieContentDetailsComponent } from './movie-content-details.component';
import { MovieContentDetailsDetailComponent } from './movie-content-details-detail.component';
import { MovieContentDetailsPopupComponent } from './movie-content-details-dialog.component';
import { MovieContentDetailsDeletePopupComponent } from './movie-content-details-delete-dialog.component';

export const movieContentDetailsRoute: Routes = [
    {
        path: 'movie-content-details',
        component: MovieContentDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContentDetails'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movie-content-details/:id',
        component: MovieContentDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContentDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieContentDetailsPopupRoute: Routes = [
    {
        path: 'movie-content-details-new',
        component: MovieContentDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContentDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-content-details/:id/edit',
        component: MovieContentDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContentDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-content-details/:id/delete',
        component: MovieContentDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContentDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
