import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MovieContentComponent } from './movie-content.component';
import { MovieContentDetailComponent } from './movie-content-detail.component';
import { MovieContentPopupComponent } from './movie-content-dialog.component';
import { MovieContentDeletePopupComponent } from './movie-content-delete-dialog.component';

export const movieContentRoute: Routes = [
    {
        path: 'movie-content',
        component: MovieContentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movie-content/:id',
        component: MovieContentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieContentPopupRoute: Routes = [
    {
        path: 'movie-content-new',
        component: MovieContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-content/:id/edit',
        component: MovieContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-content/:id/delete',
        component: MovieContentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
