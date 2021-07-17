import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MarchepublicgatewaySharedModule } from 'app/shared/shared.module';
import { MarchepublicgatewayCoreModule } from 'app/core/core.module';
import { MarchepublicgatewayAppRoutingModule } from './app-routing.module';
import { MarchepublicgatewayHomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MarchepublicgatewayEntityModule} from "app/entities/entity.module";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MarchepublicgatewaySharedModule,
    MarchepublicgatewayCoreModule,
    MarchepublicgatewayHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MarchepublicgatewayEntityModule,
    MarchepublicgatewayAppRoutingModule,
    NoopAnimationsModule
  ],
  declarations: [JhiMainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
  ],
  bootstrap: [JhiMainComponent]
})
export class MarchepublicgatewayAppModule {}
