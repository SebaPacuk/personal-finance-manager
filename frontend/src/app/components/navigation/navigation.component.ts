import {Component, OnInit} from '@angular/core';
import {User} from '../../authentication/user';
import {UserService} from '../../authentication/user.service';
import {AuthenticationService} from '../../authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import {HealthCheckTask} from '../../authentication/health-check-task';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  private currentUser: User;

  constructor(
    private userService: UserService,
    private translate: TranslateService,
    private authenticationService: AuthenticationService,
    private healthCheckTask: HealthCheckTask // TODO ugly trick to get class created, find other way
  ) {
    authenticationService.currentUserObservable.subscribe(user => {
      this.currentUser = user;
    });
    let language = sessionStorage.getItem('language');
    if (language === null || language === undefined) {
      language = 'en';
      sessionStorage.setItem('language', language);
    }

    translate.setDefaultLang(language);
  }

  ngOnInit() {
    this.currentUser = this.authenticationService.getLoggedInUser();
    if (this.currentUser.accessToken != null) { // TODO ugly trick to notify health check to start on page load when user is signed in
      this.authenticationService.updateCurrentUser(this.currentUser);
    }
  }

  logout() {
    this.authenticationService.logout();
  }

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }

  switchLanguage = (language: string) => {
    this.translate.use(language);
    sessionStorage.setItem('language', language);
  }
}
