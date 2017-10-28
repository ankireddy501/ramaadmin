/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RamaadminTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovieContentDetailComponent } from '../../../../../../main/webapp/app/entities/movie-content/movie-content-detail.component';
import { MovieContentService } from '../../../../../../main/webapp/app/entities/movie-content/movie-content.service';
import { MovieContent } from '../../../../../../main/webapp/app/entities/movie-content/movie-content.model';

describe('Component Tests', () => {

    describe('MovieContent Management Detail Component', () => {
        let comp: MovieContentDetailComponent;
        let fixture: ComponentFixture<MovieContentDetailComponent>;
        let service: MovieContentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RamaadminTestModule],
                declarations: [MovieContentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovieContentService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovieContentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovieContentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieContentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MovieContent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movieContent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
