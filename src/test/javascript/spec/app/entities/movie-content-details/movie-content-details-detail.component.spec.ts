/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { RamaadminTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MovieContentDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/movie-content-details/movie-content-details-detail.component';
import { MovieContentDetailsService } from '../../../../../../main/webapp/app/entities/movie-content-details/movie-content-details.service';
import { MovieContentDetails } from '../../../../../../main/webapp/app/entities/movie-content-details/movie-content-details.model';

describe('Component Tests', () => {

    describe('MovieContentDetails Management Detail Component', () => {
        let comp: MovieContentDetailsDetailComponent;
        let fixture: ComponentFixture<MovieContentDetailsDetailComponent>;
        let service: MovieContentDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RamaadminTestModule],
                declarations: [MovieContentDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MovieContentDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(MovieContentDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MovieContentDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieContentDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MovieContentDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.movieContentDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
