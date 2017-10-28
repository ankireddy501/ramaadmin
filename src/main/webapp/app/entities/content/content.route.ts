import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContentComponent } from './content.component';
import { ContentDetailComponent } from './content-detail.component';
import { ContentPopupComponent } from './content-dialog.component';
import { ContentDeletePopupComponent } from './content-delete-dialog.component';

export const contentRoute: Routes = [
    {
        path: 'content',
        component: ContentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'content/:id',
        component: ContentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentPopupRoute: Routes = [
    {
        path: 'content-new',
        component: ContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content/:id/edit',
        component: ContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content/:id/delete',
        component: ContentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
