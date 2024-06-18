import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Project } from 'src/app/model/project';
import { ProjectServiceService } from 'src/app/services/project-service.service';
import { UserServiceService } from 'src/app/services/user-service.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  constructor(private projectService: ProjectServiceService, private userService: UserServiceService, private router: Router) { }
  ngOnInit(): void {
    this.userService.loggedIn();
    this.getAllProjects();
    this.getUser();
  }

  projectList: Project[] = [];

  getAllProjects() {
    this.projectService.getData().subscribe((res: any) => {
      this.projectList = res;
    });
  }
  project!: Project;
  delete(id: any) {
    var members: any;
    this.projectService.getById(id).subscribe((res: any) => {
      this.project = res;
      members = this.project.members
      for (let index = 0; index < members.length; index++) {
        if (members[index] == this.user.userId) {
          console.log(members[index]);
          this.projectService.deleteByID(id).subscribe((res: any) => {
            this.router.navigateByUrl("/listProject");
          });
        } else {
          this.authorise = true
        }
      }
    })
  }

  projectList01: Project[] = [];
  getAllProjectsByMember() {
    this.owner = this.user.userId;
    this.projectService.getByMember(this.owner).subscribe((res: any) => {
      this.projectList01 = res;
      console.log(this.projectList01);
    });
  }

  owner!: number;

  authorise = false;
  getUser() {
    this.user = this.userService.getUser();
  }
  user: any;

  goToEdit(id: any, owner: any) {
    this.owner = this.user.userId;
    if (owner == this.owner) {
      this.router.navigateByUrl("/editProject/" + id);
    } else {
      this.authorise = true;
    }
  }

  dateForm: FormGroup = new FormGroup({
    startDate: new FormControl(''),
    endDate: new FormControl(''),
  });
  expression = false;
  searchOnDateStart(str: any) {
    if (str == "start") {
      this.projectService.getByDateStart(this.dateForm.value).subscribe((res: any) => {
        this.projectList = res;
      })
    } else if (str == "end") {
      this.projectService.getByDateEnd(this.dateForm.value).subscribe((res: any) => {
        this.projectList = res;
      })
    } else if (str == "custom") {
      this.projectService.getByDateCustom(this.dateForm.value).subscribe((res: any) => {
        this.projectList = res;
      })
    } else if (str == "all") {
      this.getAllProjects();
    }
  }
  str: any;
  onSelectChange(event: any) {
    this.str = event.target.value;
    if (this.str == "custom") {
      this.expression = true
    } else if (this.str == "all") {
      this.getAllProjects();
      this.expression = false
    } else {
      this.expression = false
    }
  }

  gtReport(id: any) {
    console.log(id);
    this.projectService.getByReport(id).subscribe((res: any) => {
      window.print();
      console.log("--");
    })
  }
  print(id: number){
    if(id == -1){
      return;
    }
    let url = `http://localhost:8083/listProject/report/${id}`;
    window.open(url, "_blank")
  }

  logout(){
    this.userService.logOut();
    this.router.navigateByUrl("/login");
  }
}
