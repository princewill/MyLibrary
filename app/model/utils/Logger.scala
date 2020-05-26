package model.utils


trait Logger { val logger: play.api.Logger = play.api.Logger(this.getClass) }
