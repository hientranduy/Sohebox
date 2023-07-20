import { Component, OnInit } from "@angular/core";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-phimmoi-player",
  templateUrl: "./phimmoi-player.component.html",
  styleUrls: ["./phimmoi-player.component.css"],
})
export class PhimmoiPlayerComponent implements OnInit {
  // Field movide URL
  movieUrlValue: String;
  movieUrlValuePlay: String;

  constructor(private toastr: ToastrService) {}

  ngOnInit(): void {}

  public processMovieUrl() {
    if (
      this.movieUrlValue != null &&
      this.movieUrlValue !== this.movieUrlValuePlay
    ) {
      this.movieUrlValuePlay = this.movieUrlValue;

      this.toastr.info("Playing URL:" + this.movieUrlValuePlay);
    }
  }

  public playPhimMoi() {
    const sourceTag = document.createElement("source");
    sourceTag.setAttribute(
      "src",
      "https://redirector.googlevideo.com/videoplayback?id=ec70d05769cdb825&amp;itag=22&amp;source=youtube&amp;requiressl=yes&amp;ei=ZBOIXru3OpD61gbW5LLYBw&amp;susc=ytcp&amp;mime=video/mp4&amp;dur=7436.979&amp;lmt=1585847322929884&amp;txp=2216222&amp;cmo=secure_transport=yes&amp;ip=0.0.0.0&amp;ipbits=0&amp;expire=1586004964&amp;sparams=ip,ipbits,expire,id,itag,source,requiressl,ei,susc,mime,dur,lmt&amp;signature=38340821F4DB4171A1BA468B570B4DD5CD37729D3CAEA2FF53DC53B1A1BBE97C.01663376ED4E27BFDBE4773FEAD8F9084857555DEDDC109783BD0F45B1B65F29&amp;key=us0",
    );
    document.getElementById("myPhimmoi").appendChild(sourceTag);
  }
}
