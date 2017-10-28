import { BaseEntity } from './../../shared';

export class MovieContent implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public contentPath?: string,
        public creationTime?: any,
        public updateDate?: any,
        public details?: BaseEntity,
    ) {
    }
}
