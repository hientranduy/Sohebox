import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class SEOService {
  constructor(
    private title: Title,
    private meta: Meta,
  ) {}

  updateCEO(route: ActivatedRoute) {
    if (route.snapshot.data['title']) {
      this.title.setTitle(route.snapshot.data['title']);
    }

    if (route.snapshot.data['ogUrl']) {
      this.meta.updateTag({
        name: 'og:url',
        content: route.snapshot.data['ogUrl'],
      });
    }

    if (route.snapshot.data['description']) {
      this.meta.updateTag({
        name: 'description',
        content: route.snapshot.data['description'],
      });
    }

    if (route.snapshot.data['keywords']) {
      this.meta.updateTag({
        name: 'keywords',
        content: route.snapshot.data['keywords'],
      });
    }
  }
}
