import { BaseEntity } from './../../shared';

export const enum ContentType {
    'VIDEO',
    'IMAGE'
}

export const enum SubscriptionType {
    'PAID',
    'FREE',
    'PREMIUM'
}

export class Content implements BaseEntity {
    constructor(
        public id?: number,
        public type?: ContentType,
        public subscriptionType?: SubscriptionType,
        public video?: BaseEntity,
        public rules?: BaseEntity,
        public galleries?: BaseEntity[],
    ) {
    }
}
