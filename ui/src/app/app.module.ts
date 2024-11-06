import {NgModule} from "@angular/core";
import {Grid} from "./grid/grid";
import {HttpClientModule} from "@angular/common/http";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule, RouterOutlet, Routes} from "@angular/router";
import {LevelPickerComponent} from "./level-picker/level-picker.component";

const routes: Routes = [
  { path: 'solve', component: Grid },
  { path: 'levels', component: LevelPickerComponent },
  { path: '', redirectTo: '/levels', pathMatch: 'full' }
];

@NgModule({
  imports: [HttpClientModule, BrowserModule, RouterOutlet, RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [AppComponent, Grid, LevelPickerComponent],
  bootstrap: [AppComponent]
})
export class AppModule {}
