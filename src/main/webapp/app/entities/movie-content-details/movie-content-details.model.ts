import { BaseEntity } from './../../shared';

export class MovieContentDetails implements BaseEntity {
    constructor(
        public id?: number,
        public director?: string,
        public duration?: number,
        public releaseDate?: any,
    ) {
    }
}
