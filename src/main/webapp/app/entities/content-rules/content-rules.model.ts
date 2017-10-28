import { BaseEntity } from './../../shared';

export const enum LifeTime {
    'UNLIMITED'
}

export const enum Validity {
    'UNLIMITED'
}

export class ContentRules implements BaseEntity {
    constructor(
        public id?: number,
        public lifeTime?: LifeTime,
        public validity?: Validity,
        public cost?: number,
        public content?: BaseEntity,
    ) {
    }
}
