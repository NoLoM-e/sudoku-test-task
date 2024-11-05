import {NgModule} from "@angular/core";
import {Grid} from "./grid/grid";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
  imports: [HttpClientModule, BrowserModule],
  declarations: [AppComponent, Grid],
  bootstrap: [AppComponent]
})
export class AppModule {}
