import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ContentRules } from './content-rules.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ContentRulesService {

    private resourceUrl = SERVER_API_URL + 'api/content-rules';

    constructor(private http: Http) { }

    create(contentRules: ContentRules): Observable<ContentRules> {
        const copy = this.convert(contentRules);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(contentRules: ContentRules): Observable<ContentRules> {
        const copy = this.convert(contentRules);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ContentRules> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(contentRules: ContentRules): ContentRules {
        const copy: ContentRules = Object.assign({}, contentRules);
        return copy;
    }
}
