import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ImageContent } from './image-content.model';
import { ImageContentService } from './image-content.service';

@Injectable()
export class ImageContentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private imageContentService: ImageContentService

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
                this.imageContentService.find(id).subscribe((imageContent) => {
                    if (imageContent.creationDate) {
                        imageContent.creationDate = {
                            year: imageContent.creationDate.getFullYear(),
                            month: imageContent.creationDate.getMonth() + 1,
                            day: imageContent.creationDate.getDate()
                        };
                    }
                    if (imageContent.updateDate) {
                        imageContent.updateDate = {
                            year: imageContent.updateDate.getFullYear(),
                            month: imageContent.updateDate.getMonth() + 1,
                            day: imageContent.updateDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.imageContentModalRef(component, imageContent);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.imageContentModalRef(component, new ImageContent());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    imageContentModalRef(component: Component, imageContent: ImageContent): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.imageContent = imageContent;
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
