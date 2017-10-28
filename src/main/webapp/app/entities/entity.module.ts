import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RamaadminContentModule } from './content/content.module';
import { RamaadminImageContentModule } from './image-content/image-content.module';
import { RamaadminMovieContentModule } from './movie-content/movie-content.module';
import { RamaadminMovieContentDetailsModule } from './movie-content-details/movie-content-details.module';
import { RamaadminContentRulesModule } from './content-rules/content-rules.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RamaadminContentModule,
        RamaadminImageContentModule,
        RamaadminMovieContentModule,
        RamaadminMovieContentDetailsModule,
        RamaadminContentRulesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RamaadminEntityModule {}
