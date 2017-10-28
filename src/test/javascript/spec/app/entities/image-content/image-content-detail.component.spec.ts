/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RamaadminTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ImageContentDetailComponent } from '../../../../../../main/webapp/app/entities/image-content/image-content-detail.component';
import { ImageContentService } from '../../../../../../main/webapp/app/entities/image-content/image-content.service';
import { ImageContent } from '../../../../../../main/webapp/app/entities/image-content/image-content.model';

describe('Component Tests', () => {

    describe('ImageContent Management Detail Component', () => {
        let comp: ImageContentDetailComponent;
        let fixture: ComponentFixture<ImageContentDetailComponent>;
        let service: ImageContentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RamaadminTestModule],
                declarations: [ImageContentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ImageContentService,
                    JhiEventManager
                ]
            }).overrideTemplate(ImageContentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageContentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageContentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ImageContent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.imageContent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
