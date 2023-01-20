package fr.isen.benet.androiderestaurant

import android.os.Binder


class ObjectWrapperForBinder(val data: Any) : Binder()
