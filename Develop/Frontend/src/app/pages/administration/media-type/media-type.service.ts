import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { FoodType } from "@app/_common/_models/foodType";
import { MediaType } from "@app/_common/_models/mediaType";
import { MediaTypeSCO } from "@app/_common/_sco/mediaTypeSCO";
import { environment } from "@environments/environment";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { EditMediaTypeDialogComponent } from "./_dialogs/edit-media-type.component";
import { ViewMediaTypeDialogComponent } from "./_dialogs/view-media-type.component";

@Injectable({ providedIn: "root" })
export class MediaTypeDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
    private http: HttpClient,
  ) {
    config.backdrop = "static";
    config.keyboard = false;
  }

  public edit(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditMediaTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Edit";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public view(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewMediaTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  // Search
  search(sco: MediaTypeSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/mediaTypes/search`,
      sco,
    );
  }

  // Update
  update(item: MediaType) {
    return this.http.put(`${environment.soheboxUrl}/api/mediaTypes`, item);
  }
}
