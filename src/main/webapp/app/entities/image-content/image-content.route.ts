import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ImageContentComponent } from './image-content.component';
import { ImageContentDetailComponent } from './image-content-detail.component';
import { ImageContentPopupComponent } from './image-content-dialog.component';
import { ImageContentDeletePopupComponent } from './image-content-delete-dialog.component';

export const imageContentRoute: Routes = [
    {
        path: 'image-content',
        component: ImageContentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ImageContents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'image-content/:id',
        component: ImageContentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ImageContents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imageContentPopupRoute: Routes = [
    {
        path: 'image-content-new',
        component: ImageContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ImageContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-content/:id/edit',
        component: ImageContentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ImageContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-content/:id/delete',
        component: ImageContentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ImageContents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
