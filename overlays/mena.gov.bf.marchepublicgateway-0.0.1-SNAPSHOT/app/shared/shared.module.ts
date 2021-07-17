import {LOCALE_ID, NgModule} from '@angular/core';
import { MarchepublicgatewaySharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { TableModule } from 'primeng/components/table/table';
import { ToastModule } from 'primeng/toast';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import {SpinnerModule} from 'primeng/spinner';

registerLocaleData(localeFr);

import {
  ToolbarModule,
  InputTextModule,
  PaginatorModule,
  DialogModule,
  ToggleButtonModule,
  DropdownModule,
  ButtonModule,
  MultiSelectModule,
  CheckboxModule,
  AutoCompleteModule,
  MessagesModule,
  InputSwitchModule,
  KeyFilterModule,
  RadioButtonModule,
  MegaMenuModule,
  InputMaskModule,
  TabMenuModule,
  PickListModule,
  SelectButtonModule,
  SplitButtonModule,
  ConfirmDialogModule,
  ListboxModule,
  FieldsetModule,
  ConfirmationService,
  MessageService,
  ChartModule,
  MenubarModule,
  SliderModule,
  SidebarModule,
  TabViewModule,
  EditorModule,
  OrderListModule,
  FileUploadModule, AccordionModule, TooltipModule, StepsModule
} from 'primeng/primeng';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarModule } from 'primeng/calendar';
import { PasswordStrengthBarComponent } from 'app/shared/password-check/password-strength-bar.component';
import { TableGlobalSearchDirective } from 'app/shared/directives/table-global-search.directive';
import { GanttModule } from '@syncfusion/ej2-angular-gantt';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateFRParserFormatter} from 'app/shared/util/ngb-date-fr-parser-formatter';
import {TreeModule} from 'primeng/tree';
import {DataViewModule} from 'primeng/dataview';
import {PipesModule} from 'app/pipe/PipesModule';


@NgModule({
  imports: [
    MarchepublicgatewaySharedLibsModule,
    PipesModule,
    TableModule,
    ToolbarModule,
    InputTextModule,
    SplitButtonModule,
    PaginatorModule,
    DialogModule,
    ToggleButtonModule,
    DropdownModule,
    ButtonModule,
    MultiSelectModule,
    CheckboxModule,
    AutoCompleteModule,
    MessagesModule,
    InputSwitchModule,
    KeyFilterModule,
    ToastModule,
    RadioButtonModule,
    MegaMenuModule,
    InputMaskModule,
    TabMenuModule,
    PickListModule,
    SelectButtonModule,
    ConfirmDialogModule,
    ListboxModule,
    FieldsetModule,
    ChartModule,
    CalendarModule,
    MenubarModule,
    FullCalendarModule,
    SliderModule,
    SidebarModule,
    TabViewModule,
    GanttModule,
    EditorModule,
    FileUploadModule,
    AccordionModule,
    TreeModule,
    DataViewModule,
    TooltipModule,
    StepsModule,
    SpinnerModule
  ],
  declarations: [
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    HasAnyAuthorityDirective,
    PasswordStrengthBarComponent,
    TableGlobalSearchDirective
  ],
  entryComponents: [],
  providers: [ConfirmationService, MessageService,
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    { provide: NgbDateParserFormatter, useClass: NgbDateFRParserFormatter },
  ],
  exports: [
    MarchepublicgatewaySharedLibsModule,
    PipesModule,
    FindLanguageFromKeyPipe,
    PasswordStrengthBarComponent,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    TableGlobalSearchDirective,
    HasAnyAuthorityDirective,
    TableModule,
    ToolbarModule,
    InputTextModule,
    SplitButtonModule,
    PaginatorModule,
    DialogModule,
    ToggleButtonModule,
    DropdownModule,
    ButtonModule,
    MultiSelectModule,
    CheckboxModule,
    AutoCompleteModule,
    MessagesModule,
    InputSwitchModule,
    KeyFilterModule,
    ToastModule,
    RadioButtonModule,
    MegaMenuModule,
    InputMaskModule,
    TabMenuModule,
    PickListModule,
    SelectButtonModule,
    ConfirmDialogModule,
    ListboxModule,
    FieldsetModule,
    ChartModule,
    CalendarModule,
    MenubarModule,
    FullCalendarModule,
    SliderModule,
    SidebarModule,
    TabViewModule,
    GanttModule,
    EditorModule,
    OrderListModule,
    FileUploadModule,
    AccordionModule,
    TreeModule,
    DataViewModule,
    TooltipModule,
    StepsModule,
    SpinnerModule
  ]
})
export class MarchepublicgatewaySharedModule {}
