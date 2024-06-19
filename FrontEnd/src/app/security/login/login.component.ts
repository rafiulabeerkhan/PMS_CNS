import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { UserServiceService } from 'src/app/services/user-service.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  constructor(private userService: UserServiceService, private router: Router) { }
  ngOnInit(): void {
    this.userService.loggedIn();
  }

  userForm: FormGroup = new FormGroup({
    password: new FormControl(''),
    email: new FormControl(''),
  });

  onsubmit() {
    this.userService.login(this.userForm.value).subscribe((val: any) => {
      this.userService.saveUser(val);
      const user = this.userService.getUser();
      console.log(user.userId);
      Swal.fire("Logged in Successfully!!")
      this.router.navigateByUrl("/listProject");

    })
  }
  
}
