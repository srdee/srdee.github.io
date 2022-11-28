dat_pen = droplevels(subset(penguins, species == "Adelie"))

dat_ade_f = droplevels(subset(dat_ade, sex == "female"))
dat_ade_m = droplevels(subset(dat_ade, sex == "male"))

t.test(
  x = dat_ade_m$body_mass_g,
  mu = 4000,
  alternative = "g"
)

t.test(
  x = dat_ade_f$body_mass_g,
  y = dat_ade_m$body_mass_g,
  alternative="g"
)

t.test(
  x = dat_ade_f$body_mass_g,
  y = dat_ade_m$body_mass_g,
  alternative="l"
)