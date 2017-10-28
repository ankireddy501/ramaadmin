import { BaseEntity } from './../../shared';

export class ImageContent implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public caption?: boolean,
        public contentPath?: string,
        public creationDate?: any,
        public updateDate?: any,
        public content?: BaseEntity,
    ) {
        this.caption = false;
    }
}
