package com.example.dog_app.domain.scheduler

import io.reactivex.Scheduler

interface IScheduler {

  fun computation(): Scheduler

  fun io(): Scheduler

  fun ui(): Scheduler

}
