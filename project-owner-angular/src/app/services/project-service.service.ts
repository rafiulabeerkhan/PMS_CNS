import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {

  constructor(private http: HttpClient) { }

  private url = "http://localhost:8083/project";

  addData(project: Project) {
    return this.http.post(this.url, project);
  }

  getData() {
    return this.http.get(this.url);
  }

  update(project: Project) {
    return this.http.put(this.url + "/" + project.id, project)
  }

  getById(id: any) {
    return this.http.get(this.url + "/" + id);
  }

  getByDateStart(betweenDateStart: any) {
    return this.http.post(this.url + "/startDate" , betweenDateStart );
  }

  getByDateEnd(betweenDateStart: any) {
    return this.http.post(this.url + "/endDate" , betweenDateStart );
  }

  getByDateCustom(betweenDateStart: any) {
    return this.http.post(this.url + "/customDate" , betweenDateStart );
  }

  deleteByID(id: any) {
    return this.http.delete(this.url + "/" + id)
  }

  getByMember(userId: any) {
    return this.http.post(this.url + "/member/" , userId);
  }

  getByReport(id: any) {
    return this.http.get(this.url + "/report/" + id);
  }
}
