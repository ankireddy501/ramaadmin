import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContentRulesComponent } from './content-rules.component';
import { ContentRulesDetailComponent } from './content-rules-detail.component';
import { ContentRulesPopupComponent } from './content-rules-dialog.component';
import { ContentRulesDeletePopupComponent } from './content-rules-delete-dialog.component';

export const contentRulesRoute: Routes = [
    {
        path: 'content-rules',
        component: ContentRulesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentRules'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'content-rules/:id',
        component: ContentRulesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentRules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentRulesPopupRoute: Routes = [
    {
        path: 'content-rules-new',
        component: ContentRulesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentRules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content-rules/:id/edit',
        component: ContentRulesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentRules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'content-rules/:id/delete',
        component: ContentRulesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContentRules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
