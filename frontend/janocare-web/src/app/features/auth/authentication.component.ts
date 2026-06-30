import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { routes } from '../../shared/routes/routes';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  standalone: true,
  imports: [RouterOutlet],
})
export class AuthenticationComponent {
  public routes = routes;
}
