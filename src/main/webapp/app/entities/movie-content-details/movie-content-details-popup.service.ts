import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MovieContentDetails } from './movie-content-details.model';
import { MovieContentDetailsService } from './movie-content-details.service';

@Injectable()
export class MovieContentDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private movieContentDetailsService: MovieContentDetailsService

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
                this.movieContentDetailsService.find(id).subscribe((movieContentDetails) => {
                    if (movieContentDetails.releaseDate) {
                        movieContentDetails.releaseDate = {
                            year: movieContentDetails.releaseDate.getFullYear(),
                            month: movieContentDetails.releaseDate.getMonth() + 1,
                            day: movieContentDetails.releaseDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.movieContentDetailsModalRef(component, movieContentDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.movieContentDetailsModalRef(component, new MovieContentDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    movieContentDetailsModalRef(component: Component, movieContentDetails: MovieContentDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.movieContentDetails = movieContentDetails;
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
