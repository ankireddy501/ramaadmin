/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RamaadminTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContentRulesDetailComponent } from '../../../../../../main/webapp/app/entities/content-rules/content-rules-detail.component';
import { ContentRulesService } from '../../../../../../main/webapp/app/entities/content-rules/content-rules.service';
import { ContentRules } from '../../../../../../main/webapp/app/entities/content-rules/content-rules.model';

describe('Component Tests', () => {

    describe('ContentRules Management Detail Component', () => {
        let comp: ContentRulesDetailComponent;
        let fixture: ComponentFixture<ContentRulesDetailComponent>;
        let service: ContentRulesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RamaadminTestModule],
                declarations: [ContentRulesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContentRulesService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContentRulesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContentRulesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentRulesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContentRules(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contentRules).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
