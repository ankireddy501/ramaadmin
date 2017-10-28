import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MovieContent } from './movie-content.model';
import { MovieContentService } from './movie-content.service';

@Injectable()
export class MovieContentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private movieContentService: MovieContentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.movieContentService.find(id).subscribe((movieContent) => {
                    if (movieContent.creationTime) {
                        movieContent.creationTime = {
                            year: movieContent.creationTime.getFullYear(),
                            month: movieContent.creationTime.getMonth() + 1,
                            day: movieContent.creationTime.getDate()
                        };
                    }
                    if (movieContent.updateDate) {
                        movieContent.updateDate = {
                            year: movieContent.updateDate.getFullYear(),
                            month: movieContent.updateDate.getMonth() + 1,
                            day: movieContent.updateDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.movieContentModalRef(component, movieContent);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.movieContentModalRef(component, new MovieContent());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    movieContentModalRef(component: Component, movieContent: MovieContent): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.movieContent = movieContent;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
